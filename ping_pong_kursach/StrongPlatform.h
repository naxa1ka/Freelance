#pragma once
#include "EnemyPlatform.h"
class StrongPlatform : public EnemyPlatform
{
public:
	StrongPlatform(int InitX, int InitY, int InitWidth, int InitHeight);
	~StrongPlatform();
	virtual HPEN SetColor() override;
};

