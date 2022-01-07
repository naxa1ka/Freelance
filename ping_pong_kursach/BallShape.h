#pragma once
#include "IShape.h"
extern HDC hdc;
class BallShape: public IShape
{
	virtual void Show(int Radius, int X, int Y) {
		Ellipse(hdc, X - Radius, Y + Radius, X + Radius, Y - Radius);
	}
};

