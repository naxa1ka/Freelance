#pragma once
#include <Windows.h>
class IWheel 
{
public:
	virtual void Show(int X, int Y, int Scale) = 0;
};

