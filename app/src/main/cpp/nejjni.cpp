#include<jni.h>
extern "C"{
	JNIEXPORT jstring JNICALL Java_com_mycompany_myapp2_MainActivity_resJni(JNIEnv *env,jobject thiz){
		return (*env)->NewStringUTF(env, "Hello from JNI ! Compiled with ABI " ABI ".");
	}
}
