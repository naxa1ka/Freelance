#pragma once
#include "Platform.h"
#include "PLATFORMTYPE.h"
#include "IColorable.h"
class EnemyPlatform : public Platform, public IColorable
{
protected:
	int Health;
	PLATFORMTYPE type;
public:
	EnemyPlatform(int InitX, int InitY, int InitWidth, int InitHeight);
	~EnemyPlatform();
	void virtual Show() override;
	PLATFORMTYPE GetType() { return type; }
	int GetHealth() { return Health; }
	void Damage() { Health--; }
	virtual HPEN SetColor() override;
		
};

