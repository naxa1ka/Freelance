#pragma once
#include <Windows.h>
#include "IRoof.h"
extern HDC hdc;
class RectRoof : public IRoof
{
public:
	virtual void Show(int X, int Y, int Scale) override {
		Rectangle(hdc, X - 4 * Scale, Y-Scale*1+1, X + 6 * Scale, Y - Scale * 3);
	}
};

