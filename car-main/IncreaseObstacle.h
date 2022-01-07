#pragma once
#include "Obstacle.h"
class IncreaseObstacle : public Obstacle
{
private:
	virtual HPEN SetColor() override;
public:
	IncreaseObstacle(int InitX, int InitY, int Scale);
};

