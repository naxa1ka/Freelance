#pragma once
#include <Windows.h>
#include "IBody.h"
extern HDC hdc;
class BaseBody : public IBody
{
public:
	virtual void Show(int X, int Y, int Scale) override {
		POINT body[7] = {
	   {X - 8 * Scale, Y + Scale * 2}, {X - 8 * Scale, Y}, {X - 6 * Scale, Y - Scale * 1}, {X + 7 * Scale, Y - Scale * 1},
	   {X + 8 * Scale, Y}, {X + 8 * Scale, Y + Scale * 2}, {X - 8 * Scale, Y + Scale * 2}
		};
		Polyline(hdc, body, 7);
	}
};

