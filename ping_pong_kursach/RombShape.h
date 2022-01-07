#pragma once
#include "IShape.h"
extern HDC hdc;
class RombShape : public IShape
{
	virtual void Show(int Radius, int X, int Y) {
		MoveToEx(hdc, X, Y+Radius, NULL);
		LineTo(hdc, X + Radius, Y);

		MoveToEx(hdc, X, Y + Radius, NULL);
		LineTo(hdc, X - Radius, Y);

		MoveToEx(hdc, X, Y - Radius, NULL);
		LineTo(hdc, X + Radius, Y);

		MoveToEx(hdc, X, Y - Radius, NULL);
		LineTo(hdc, X - Radius, Y);
	}
};

