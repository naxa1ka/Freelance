#pragma once
#include "EnemyPlatform.h"
class TransparentPlatform : public EnemyPlatform
{
public:
	TransparentPlatform(int InitX, int InitY, int InitWidth, int InitHeight);
	~TransparentPlatform();
	virtual HPEN SetColor() override;
};

