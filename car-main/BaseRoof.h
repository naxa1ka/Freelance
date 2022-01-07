#pragma once
#include <Windows.h>
#include "IRoof.h"
extern HDC hdc;
class BaseRoof : public IRoof
{
public:
	virtual void Show(int X, int Y, int Scale) override {
		POINT roof[4] = {
		{X - 5 * Scale, Y - Scale * 1}, {X - 3 * Scale, Y - Scale * 3}, {X + 4 * Scale, Y - Scale * 3},
		{X + 6 * Scale, Y - Scale * 1}
		};
		Polyline(hdc, roof, 4);
	}
};

