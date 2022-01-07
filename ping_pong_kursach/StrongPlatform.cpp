#include "StrongPlatform.h"

StrongPlatform::StrongPlatform(int InitX, int InitY, int InitWidth, int InitHeight) : EnemyPlatform(InitX, InitY, InitWidth, InitHeight)
{
	Health = 2;
	type = STRONG_PLATFORM;
}

StrongPlatform::~StrongPlatform()
{
}

HPEN StrongPlatform::SetColor()
{
	return CreatePen(PS_SOLID, 2, RGB(128, 128, 0));
}
