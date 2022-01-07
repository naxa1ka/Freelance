#include "DecreaseObstacle.h"
#include "Windows.h"

extern HDC hdc;

DecreaseObstacle::DecreaseObstacle(int InitX, int InitY, int Scale) : Obstacle(InitX, InitY, Scale) {
	Type = DEACREASE;
}

HPEN DecreaseObstacle::SetColor()
{
	return CreatePen(PS_SOLID, 2, RGB(255,255, 0));
}