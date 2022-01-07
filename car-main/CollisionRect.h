#pragma once

#include "Point.h"

class CollisionRect : public Point { 
                        
protected:
    int CollisionWidth;
    int CollisionHeight;
public:
    CollisionRect(int InitX, int InitY, int InitWidth, int InitHeight);

    CollisionRect(int InitX, int InitY);

    CollisionRect();

    ~CollisionRect();

    int GetCollisionWidth();

    int GetCollisionHeight();

    virtual void Show() override;

    virtual void Hide() override;
};

