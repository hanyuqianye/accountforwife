@echo off
rem ���
jar cvfm core.jar MANIFEST.MF -C ..\..\bin\ .

rem �ÿ�ִ���ļ���װjava��������
windres account.rc account-res.o
gcc wrap.c account-res.o -o С������.exe

rem ������
mkdir dependencies
xcopy ..\..\dependencies .\dependencies

rem ��ʼ�����ݿ�
sqlite3 Account.db < ..\src\Account.sql

python zip-all.py

rem ����ļ�
del /f /q dependencies
rmdir dependencies
del Account.db
del С������.exe
del core.jar
del account-res.o