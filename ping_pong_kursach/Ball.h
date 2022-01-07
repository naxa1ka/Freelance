#pragma once
#include "Square.h"
#include "Direction.h"
#include "BlackColor.h"
#include "GreenColor.h"
#include "RedColor.h"
#include "BlueColor.h"
#include "IColorable.h"
#include "IShape.h"
#include "BallShape.h"
#include "RectShape.h"
#include "TriangleShape.h"
#include "RombShape.h"
class Ball : public Square
{
protected:
	DIRECTION direction = RIGHT_UP;

	IColorable *color;
	IColorable *colors[4];

	IShape* shape;
	IShape* shapes[4];

	int ColorLvl = 0;
	int ShapeLvl = 0;

	int BaseSpeedX = 2;
	int BaseSpeedY = 4;

	int CurrSpeedX;
	int CurrSpeedY;
	
public:
	Ball(int InitX, int InitY, int InitRadius);
	~Ball();
	virtual void Show() override;
	void MoveTo();
	void MoveTo(int InitX, int InitY);
	void ChangeDirection();
	void IncreaseColorLevel();
	void IncreaseShapeLevel();
	void SetDirection(DIRECTION direction);
};

