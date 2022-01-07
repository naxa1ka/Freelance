#include "Ball.h"
#include <iostream>
#include <Windows.h>
extern HDC hdc;

Ball::Ball(int InitX, int InitY, int InitRadius) : Square(InitX, InitY, InitRadius)
{
	colors[0] = new BlackColor;
	colors[1] = new RedColor;
	colors[2] = new GreenColor;
	colors[3] = new BlueColor;
	
	shapes[3] = new BallShape;
	shapes[0] = new RectShape;
	shapes[2] = new TriangleShape;
	shapes[1] = new RombShape;

	color = colors[0];
	shape = shapes[0];

	CurrSpeedX = BaseSpeedX;
	CurrSpeedY = BaseSpeedY;
}

Ball::~Ball()
{
}

void Ball::Show()
{
	HPEN pen = color->SetColor();
	SelectObject(hdc, pen);
	shape->Show(Radius, X, Y);
	DeleteObject(pen);
}

void Ball::MoveTo(int InitX, int InitY)
{
	Point::MoveTo(InitX, InitY);
}

void Ball::MoveTo()
{
	Point::MoveTo(X, Y);
	X += CurrSpeedX;
	Y += CurrSpeedY;
	Sleep(10);
}

void Ball::ChangeDirection()
{
	switch (direction)
	{
	case RIGHT_DOWN:
		this->direction = RIGHT_UP;
		CurrSpeedX = BaseSpeedX + rand() % BaseSpeedX * 2;
		CurrSpeedY = -BaseSpeedY - rand() % BaseSpeedY * 2;
		break;
	case RIGHT_UP:
		this->direction = LEFT_UP;
		CurrSpeedX = -BaseSpeedX - rand() % BaseSpeedX * 2;
		CurrSpeedY = -BaseSpeedY - rand() % BaseSpeedY * 2;
		break;
	case LEFT_UP:
		this->direction = LEFT_DOWN;
		CurrSpeedX = -BaseSpeedX - rand() % BaseSpeedX * 2;
		CurrSpeedY = BaseSpeedY + rand() % BaseSpeedY * 2;
		break;
	case LEFT_DOWN:
		this->direction = RIGHT_DOWN;
		CurrSpeedX = BaseSpeedX + rand() % BaseSpeedX * 2;
		CurrSpeedY = BaseSpeedY + rand() % BaseSpeedY * 2;
		break;
	default:
		break;
	}
}


void Ball::IncreaseColorLevel()
{
	if (ColorLvl != 3)
	{
		ColorLvl++;
		color = colors[ColorLvl];
	}
}

void Ball::IncreaseShapeLevel()
{
	if (ShapeLvl != 3)
	{
		ShapeLvl++;
		shape = shapes[ShapeLvl];
	}
}

void Ball::SetDirection(DIRECTION direction)
{
	this->direction = direction; 
	ChangeDirection();
}

