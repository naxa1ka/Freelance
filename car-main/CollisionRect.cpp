#include "CollisionRect.h"
#include <Windows.h>

extern HDC hdc;

CollisionRect::CollisionRect(int InitX, int InitY, int InitWidth, int InitHeight) : Point(InitX, InitY) {
    CollisionWidth = InitWidth;
    CollisionHeight = InitHeight;
}

CollisionRect::CollisionRect(int InitX, int InitY) : Point(InitX, InitY) {
}

CollisionRect::CollisionRect() : Point()
{
}

CollisionRect::~CollisionRect() {
}

void CollisionRect::Show() { //рисуем прямоугольник по которой производится проверка столкновений
    HPEN pen = CreatePen(PS_DOT, 1, RGB(255, 0, 0));
    SelectObject(hdc, pen);
    Rectangle(hdc, X - CollisionWidth, Y + CollisionHeight, X + CollisionWidth, Y - CollisionHeight);
    DeleteObject(pen);
}

void CollisionRect::Hide() { //скрываем
    RECT r;
    r.left = X - CollisionWidth - 3;
    r.top = Y + CollisionHeight + 3;
    r.right = X + CollisionWidth + 3;
    r.bottom = Y - CollisionHeight - 3;

    HBRUSH brush = CreateSolidBrush(RGB(255, 255, 255));
    FillRect(hdc, &r, brush);
    DeleteObject(brush);
}

int CollisionRect::GetCollisionWidth() {
    return CollisionWidth;
}

int CollisionRect::GetCollisionHeight() {
    return CollisionHeight;
}
