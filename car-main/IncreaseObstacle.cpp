#include "IncreaseObstacle.h"
#include "Windows.h"

extern HDC hdc;

IncreaseObstacle::IncreaseObstacle(int InitX, int InitY, int Scale) : Obstacle(InitX, InitY, Scale) {
	Type = INCREASE;
}

HPEN IncreaseObstacle::SetColor()
{
	return CreatePen(PS_SOLID, 2, RGB(0, 255, 255));
}