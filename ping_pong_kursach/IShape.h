#pragma once
#include <Windows.h>
class IShape //интерфейс для покраски объектов
{
public:
	virtual void Show(int Radius, int X, int Y) = 0;
};

