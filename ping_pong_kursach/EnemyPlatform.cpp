#include "EnemyPlatform.h"
extern HDC hdc;
EnemyPlatform::EnemyPlatform(int InitX, int InitY, int InitWidth, int InitHeight) : Platform(InitX, InitY, InitWidth, InitHeight)
{
	Health = 1;
	type = ENEMY_PLATFORM;
}

EnemyPlatform::~EnemyPlatform()
{
}

void EnemyPlatform::Show()
{
	HPEN pen = SetColor();
	SelectObject(hdc, pen);
	Rectangle(hdc, X - Width, Y + Height, X + Width, Y - Height);
	DeleteObject(pen);
}

HPEN EnemyPlatform::SetColor()
{
	return CreatePen(PS_SOLID, 2, RGB(255, 128, 255));
}
