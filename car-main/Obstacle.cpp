#include "Obstacle.h"
#include "Windows.h"

extern HDC hdc;

Obstacle::Obstacle(int InitX, int InitY, int Scale) : CollisionRect(InitX, InitY) {
    this->Scale = Scale;
    CollisionHeight = CollisionWidth = Scale;
}

void Obstacle::Show()
{
    CollisionRect::Show();
    HPEN pen = SetColor();
    SelectObject(hdc, pen);
    Ellipse(hdc, X - Scale, Y - Scale, X + Scale, Y + Scale);
    DeleteObject(pen);
}

ObstacleType Obstacle::GetType()
{
    return Type;
}
