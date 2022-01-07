#include "Rect.h"
#include <Windows.h>

extern HDC hdc;

Rect::Rect(int InitX, int InitY, int InitWidth, int InitHeight) : CollisionRect(InitX, InitY) {
    CollisionWidth = Width = InitWidth;
    CollisionHeight = Height = InitHeight;
}

Rect::Rect(int InitX, int InitY) : CollisionRect(InitX, InitY) {
}

Rect::~Rect() {
}

void Rect::Show() { //рисуем прямоугольник
    HPEN pen = CreatePen(PS_SOLID, 1, RGB(0, 0, 0));
    SelectObject(hdc, pen);
    Rectangle(hdc, X - Width, Y + Height, X + Width, Y - Height);
    DeleteObject(pen);
}

int Rect::GetWidth() {
    return Width;
}

int Rect::GetHeight() {
    return Height;
}
