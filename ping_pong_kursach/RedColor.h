#pragma once
#include "IColorable.h"
class RedColor : public IColorable
{
	virtual HPEN SetColor() override {
		return CreatePen(PS_SOLID, 1, RGB(255, 0, 0));
	}
};

