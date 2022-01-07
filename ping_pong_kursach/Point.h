#pragma once

#include "Location.h"

class Point : public Location { //����� �������������� �� ���� ����� �� ������
protected:
    bool Visible; //�������� ��������� �� ������
public:
    Point(int InitX, int InitY); //����������� �����

    Point();

    ~Point(); //����������

    bool IsVisible(); //������ ��� ���� ���������  

    void MoveTo(int NewX, int NewY); //������������ �����

    virtual void Show(); //����� ����� �� ������

    virtual void Hide();//������ ����� �� ������
};