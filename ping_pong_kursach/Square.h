#pragma once
#include "CollisionRect.h"
class Square : public CollisionRect {
protected:
    int Radius;
public:
    Square(int InitX, int InitY, int InitRadius);

    Square(int InitX, int InitY);

    ~Square();

    int GetRadius();

    virtual void Show() override;
};