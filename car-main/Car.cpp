#include "Car.h"
#include "Windows.h"

extern HDC hdc;

Car::Car(int InitX, int InitY) : CollisionRect(InitX, InitY) {
	CollisionHeight = Scale * 4;
	CollisionWidth = Scale * 9;

    
    wheel = new RectWheel();

    roof = new RectRoof();

    body = new BaseBody();

}

void Car::Show()
{   
    CollisionRect::Show();
   
    color = new BlueColor();
    HPEN bodyPen = color->SetColor();
    SelectObject(hdc, bodyPen);
    body->Show(X, Y, Scale);
    DeleteObject(bodyPen);

    color = new GreenColor();
    HPEN roofPen = color->SetColor();
    SelectObject(hdc, roofPen);
    roof->Show(X, Y, Scale);
    DeleteObject(roofPen);

    color = new BlackColor();
    HPEN wheelPen = color->SetColor();
    SelectObject(hdc, wheelPen);
    wheel->Show(X, Y, Scale);
    DeleteObject(wheelPen);
}

void Car::IncreaseWheel()
{
    wheel = new SquareWheel();
}

void Car::DeacreaseWheel()
{
    wheel = new RectWheel();
}

void Car::IncreaseRoof()
{
    roof = new BaseRoof();
}

void Car::DeacreaseRoof()
{
    roof = new RectRoof();
}

void Car::DeacreaseBody()
{
    
    body = new RectBody();
}

void Car::IncreaseBody()
{
    body = new BaseBody();
}

void Car::DeacreaseSize()
{
    if (Scale > 5) {
        Scale -= 1;
        UpdateSize();
    }
}

void Car::UpdateSize()
{
    CollisionHeight = Scale * 4;
    CollisionWidth = Scale * 9;
}

void Car::IncreaseSize()
{
    Scale += 1;
    UpdateSize();
}
