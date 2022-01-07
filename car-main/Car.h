#pragma once
#include "CollisionRect.h"
#include "SquareWheel.h"
#include "RectWheel.h"
#include "BaseRoof.h"
#include "BaseBody.h"
#include "RectBody.h"
#include "RectRoof.h"
#include "BlueColor.h"
#include "BlackColor.h"
#include "GreenColor.h"
class Car : public CollisionRect
{
protected:
	IWheel* wheel;
	IRoof* roof;
	IBody* body;
	IColorable* color;
	int Scale = 10;
public:
	Car(int InitX, int InitY);
	virtual void Show() override;

	void IncreaseWheel();
	void DeacreaseWheel();

	void IncreaseRoof();
	void DeacreaseRoof();

	void IncreaseBody();
	void DeacreaseBody();
	
	void IncreaseSize();
	void DeacreaseSize();
private:
	void UpdateSize();
};
