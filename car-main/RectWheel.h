#pragma once
#include <Windows.h>
#include "IWheel.h"
extern HDC hdc;
class RectWheel : public IWheel
{
public:
	virtual void Show(int X, int Y, int Scale) override {
		Rectangle(hdc, X - 6 * Scale, Y + 3 * Scale, X - 4 * Scale, Y + 1 * Scale);
		Rectangle(hdc, X + 6 * Scale, Y + 3 * Scale, X + 4 * Scale, Y + 1 * Scale);
	}
};

