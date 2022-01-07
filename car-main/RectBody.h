#pragma once
#include <Windows.h>
#include "IBody.h"
extern HDC hdc;
class RectBody : public IBody
{
public:
	virtual void Show(int X, int Y, int Scale) override {
		Rectangle(hdc, X - 8 * Scale, Y - Scale * 1, X + 8 * Scale, Y+Scale*2);
	}
};

