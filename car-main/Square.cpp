#include "Square.h"
#include <Windows.h>

extern HDC hdc;

Square::Square(int InitX, int InitY, int InitRadius) : CollisionRect(InitX, InitY) {
    Radius = InitRadius;
    CollisionHeight = CollisionWidth = Radius+5;
}

Square::Square(int InitX, int InitY) : CollisionRect(InitX, InitY) {
}

Square::~Square() {
}

void Square::Show() { //рисуем прямоугольник
    HPEN pen = CreatePen(PS_SOLID, 1, RGB(0, 0, 0));
    SelectObject(hdc, pen);
    Ellipse(hdc, X - Radius, Y + Radius, X + Radius, Y - Radius);
    DeleteObject(pen);

}

int Square::GetRadius() {
    return Radius;
}
