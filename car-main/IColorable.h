#pragma once
#include <Windows.h>
class IColorable //интерфейс для покраски объектов
{
public:
	virtual HPEN SetColor() = 0;
};

