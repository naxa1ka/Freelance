#pragma once
#include "Obstacle.h"
class DecreaseObstacle : public Obstacle
{
private:
	virtual HPEN SetColor() override;
public:
	DecreaseObstacle(int InitX, int InitY, int Scale);
};

