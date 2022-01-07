#pragma once
#include "Rect.h"
class Wall : public Rect {
public:
	Wall(int InitX, int InitY, int InitWidth, int InitHeight);
	~Wall();
};

