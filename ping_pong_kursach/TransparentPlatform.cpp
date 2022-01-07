#include "TransparentPlatform.h"

TransparentPlatform::TransparentPlatform(int InitX, int InitY, int InitWidth, int InitHeight): EnemyPlatform(InitX, InitY, InitWidth, InitHeight)
{
	Health = 1;
	type = TRANSPARENT_PLATFORM;
}

TransparentPlatform::~TransparentPlatform()
{
}

HPEN TransparentPlatform::SetColor()
{
	return CreatePen(PS_SOLID, 1, RGB(128, 256, 128));
}
