#pragma once
#include "IShape.h"
extern HDC hdc;
class RectShape : public IShape
{
	virtual void Show(int Radius, int X, int Y) {
		Rectangle(hdc, X - Radius, Y + Radius, X + Radius, Y - Radius);
	}
};

