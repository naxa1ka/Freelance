#include <windows.h>
#include "GetConsolWindow.h"
/*----------------------------------------------------------------------*/
/*  ������� ���������� ����  */
/*---------------------------*/
HWND GetConcolWindow()
{
	char str[128];
	// char title[128]="xxxxxxxxxxxxxxxxxx";
	LPWSTR title = (LPWSTR)"xxxxxxxxxxxxxxxxxx";	//����� ������ Windows
	GetConsoleTitle((LPWSTR)str, sizeof((LPWSTR)str)); // �������� ��������� ����
	SetConsoleTitle(title);						// ���������� ����� ��������� ����
	Sleep(100);									// ���� ����� ��������� ���� (100 ��);

	HWND hwnd = FindWindow(NULL, (LPWSTR)title);// ���������� ���������� ����
	MoveWindow(hwnd, 0, 0, 800, 600, FALSE);
	SetConsoleTitle((LPWSTR)str);				//���������� ������� ���������

	return hwnd;//������� ���������� ����
}//end GetConcolWindow()

 /******************   End of File GetConWin.cpp  *****************/