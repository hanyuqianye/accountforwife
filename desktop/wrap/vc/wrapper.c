#include <windows.h>
#include <jni.h>

#define PATH_SEPARATOR ';' /* define it to be ':' on Solaris */
#define USER_CLASSPATH "core.jar;dependencies/nachocalendar-0.23.jar;dependencies/jcommon-1.0.16.jar;dependencies/jfreechart-1.0.13.jar;dependencies/poi-3.7-20101029.jar;dependencies/sqlitejdbc-v056.jar"

int __stdcall WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, LPSTR lpCmdLine, int nCmdShow){
     JNIEnv *env;
     JavaVM *jvm;
     jint res;
     jclass cls;
     jmethodID mid;
     jstring jstr;
     jclass stringClass;
     jobjectArray args;

 #ifdef JNI_VERSION_1_2
     JavaVMInitArgs vm_args;
     JavaVMOption options[1];
     options[0].optionString =
         "-Djava.class.path=" USER_CLASSPATH;
     vm_args.version = 0x00010002;
     vm_args.options = options;
     vm_args.nOptions = 1;
     vm_args.ignoreUnrecognized = JNI_TRUE;
     /* Create the Java VM */
     res = JNI_CreateJavaVM(&jvm, (void**)&env, &vm_args);
 #else
     JDK1_1InitArgs vm_args;
     char classpath[1024];
     vm_args.version = 0x00010001;
     JNI_GetDefaultJavaVMInitArgs(&vm_args);
     /* Append USER_CLASSPATH to the default system class path */
     sprintf(classpath, "%s%c%s",
             vm_args.classpath, PATH_SEPARATOR, USER_CLASSPATH);
     vm_args.classpath = classpath;
     /* Create the Java VM */
     res = JNI_CreateJavaVM(&jvm, &env, &vm_args);
 #endif /* JNI_VERSION_1_2 */

     if (res < 0) {
         fprintf(stderr, "Can't create Java VM\n");
         exit(1);
     }
     cls = (*env)->FindClass(env, "cat/AccountPanel");
     if (cls == NULL) {
         goto destroy;
     }

     mid = (*env)->GetStaticMethodID(env, cls, "main", "([Ljava/lang/String;)V");
     if (mid == NULL) {
         goto destroy;
     }
     /*jstr = (*env)->NewStringUTF(env, " from C!");
     if (jstr == NULL) {
         goto destroy;
     }
     stringClass = (*env)->FindClass(env, "java/lang/String");
     args = (*env)->NewObjectArray(env, 1, stringClass, jstr);
     if (args == NULL) {
         goto destroy;
     }*/
     (*env)->CallStaticVoidMethod(env, cls, mid, NULL);

 destroy:
     if ((*env)->ExceptionOccurred(env)) {
         (*env)->ExceptionDescribe(env);
     }
     (*jvm)->DestroyJavaVM(jvm);
 }
