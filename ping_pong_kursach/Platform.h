#pragma once
#include "Rect.h"
class Platform : public Rect{
public:
	Platform(int InitX, int InitY, int InitWidth, int InitHeight);
	~Platform();
};

