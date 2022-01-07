#pragma once
#include "CollisionRect.h"
#include "IColorable.h"
#include "ObstacleType.h"
class Obstacle : public CollisionRect, public IColorable
{
private:
	virtual HPEN SetColor() = 0;
protected:
	int Scale;
	ObstacleType Type;
public:
	Obstacle(int InitX, int InitY, int Scale);
	virtual void Show() override;
	ObstacleType GetType();
};
