#include <jni.h>
#include <string>
#include<thread>
#include <__threading_support>
#include <iostream>
#include <dirent.h>
#include <unistd.h>
#include "android/log.h"
#include "unistd.h"
#include <sys/syscall.h>
#include <string>
#include <vector>
#include <signal.h>
//#include "execinfo.h"

using namespace std;

char *TAG = "djd";

//定义变量接收线程id,它是一个线程的标识符。
//Linux中，每个进程有一个pid，类型pid_t，由getpid()取得。
//Linux下的POSIX线程也有一个id，类型 pthread_t，由pthread_self()取得，该id由线程库维护，其id空间是各个进程独立的
//(即不同进程中的线程可能有相同的id）
pthread_t pThreadId;

char *threadName = "djd thread";

int32_t mainThreadId;
int32_t flutterUIThreadId;
char name[16];

void signalHandler(int signal, siginfo *info, void *context_) {
    //当前handler运行在Flutter的UI线程
    __android_log_print(ANDROID_LOG_INFO, TAG, "signalHandler called:%d", signal);
    int32_t thisThreadId = static_cast<int32_t>(syscall(__NR_gettid));
    __android_log_print(ANDROID_LOG_INFO, TAG, "in handler thisThread=%d", thisThreadId);
    if (signal != SIGPROF) {
        return;
    }
    //typedef struct ucontext
    //  {
    //    unsigned long int uc_flags;
    //    struct ucontext *uc_link;// 当前上下文执行完了，恢复运行的上下文
    //    stack_t uc_stack;// 该上下文中使用的栈
    //    mcontext_t uc_mcontext;// 保存当前上下文，即各个寄存器的状态
    //    __sigset_t uc_sigmask;// 保存当前线程的信号屏蔽掩码
    //    struct _libc_fpstate __fpregs_mem;
    //  } ucontext_t;

    ucontext_t *context = reinterpret_cast<ucontext_t *>(context_);
    //描述整个上下文
    //typedef struct
    //  {
    //    gregset_t gregs;//用于装载寄存器
    //    /* Note that fpregs is a pointer.  */
    //    fpregset_t fpregs;//所有寄存器的类型
    //    __extension__ unsigned long long __reserved1 [8];
    //} mcontext_t;
    mcontext_t mcontext = context->uc_mcontext;
    __android_log_print(ANDROID_LOG_INFO, TAG, "mcontext.pc = %llu", mcontext.pc);
    __android_log_print(ANDROID_LOG_INFO, TAG, "mcontext.pstate = %llu", mcontext.pstate);
    __android_log_print(ANDROID_LOG_INFO, TAG, "mcontext.sp = %llu", mcontext.sp);
    __android_log_print(ANDROID_LOG_INFO, TAG, "mcontext.fault_address = %llu", mcontext.fault_address);
    __android_log_print(ANDROID_LOG_INFO, TAG, "mcontext.__reserved = %s", mcontext.__reserved);



};

//线程的主主体方法, 相当于Java中的run
[[noreturn]] void *thread_entity(void *pVoid) {
    struct sigaction act = {};
    act.sa_sigaction = signalHandler;
    sigemptyset(&act.sa_mask);
    act.sa_flags = SA_RESTART | SA_SIGINFO | SA_ONSTACK;
    sigaction(SIGPROF, &act, NULL);

    int32_t pid = static_cast<int32_t>(getpid());
    int32_t thisThreadId = static_cast<int32_t>(syscall(__NR_gettid));
    __android_log_print(ANDROID_LOG_INFO, TAG, "mainThread = %d", mainThreadId);
    __android_log_print(ANDROID_LOG_INFO, TAG, "in sub thisThread = %d", thisThreadId);

    while (true) {
        usleep(100000);
        syscall(__NR_tgkill, pid, flutterUIThreadId, SIGPROF);
    }
}

void initFlutterUIThreadId() {
    FILE *fpipe;
    char *command = "/system/bin/ps";
    char *args = " -T -p ";
    char flutterUiThread[16];
    string pid = to_string(mainThreadId);
    string cmd = string(command) + string(args) + pid;
    char line[256];
    if (!(fpipe = (FILE *) popen(cmd.c_str(), "r"))) exit(1);
    while (fgets(line, sizeof line, fpipe)) {
        if (strstr(line, "1.ui") != NULL) {
            __android_log_print(ANDROID_LOG_INFO, TAG, "ps info  %s", line);
            vector<char *> res_split;
            int i = 0;
            char *substr = strtok(line, " ");
            while (i < 2) {
                substr = strtok(NULL, " ");
                i++;
            }
            flutterUIThreadId = atoi(substr);
        }
    }
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_taobao_idlefish_flutterboostexample_ndk_JNI_stringFromJNI(
        JNIEnv *env,
        jclass thiz) {
    std::string hello = "Hello from C++";
    // syscall() 执行一个系统调用，根据指定的参数number和所有系统调用的汇编语言接口来确定调用哪个系统调用。
    mainThreadId = static_cast<int32_t>(syscall(__NR_gettid));

    __android_log_print(ANDROID_LOG_INFO, TAG, "in main thisThread=%d", mainThreadId);
    pthread_setname_np(pThreadId, threadName);

    initFlutterUIThreadId();
    //第一个参数为指向线程标识符的指针，第二个参数用来设置线程属性，第三个参数是线程运行函数的起始地址，最后一个参数是运行函数的参数。
    // 这里，我们的函数thread不需要参数，所以最后一个参数设为空指针
    pthread_create(&pThreadId, NULL, thread_entity, NULL);
    return env->NewStringUTF(hello.c_str());

}


