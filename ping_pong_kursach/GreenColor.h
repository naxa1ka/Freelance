#pragma once
#include "IColorable.h"
class GreenColor : public IColorable
{
	virtual HPEN SetColor() override {
		return CreatePen(PS_SOLID, 1, RGB(0, 255, 0));
	}
};
