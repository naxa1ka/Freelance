#pragma once

class Location { //����� ��� ���������
protected:
    int X;  //���������� �� �����������
    int Y;  //���������� �� ���������
public:
    Location(int InitX, int InitY);//�����������

    Location();

    ~Location(); //����������

    int GetX(); //������ ��� ����

    int GetY(); //������ ��� ������
};
