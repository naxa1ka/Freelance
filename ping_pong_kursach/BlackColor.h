#pragma once
#include "IColorable.h"
class BlackColor : public IColorable
{
	virtual HPEN SetColor() override {
		return CreatePen(PS_SOLID, 1, RGB(0, 0, 0));
	}
};

