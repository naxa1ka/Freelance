#pragma once                  
#include <windows.h>
#include <conio.h>
#include <iostream>   
#include <thread>
#include <chrono>
     
#include "GetConsolWindow.h"	
#include "CollisionRect.h"	
#include "IncreaseObstacle.h"	
#include "DecreaseObstacle.h"	
#include "Car.h"	

using namespace std; // Пространство имен std
//макрос для определения кода нажатой клавиши
#define KEY_DOWN(vk_code) ((GetAsyncKeyState(vk_code) & 0x8000) ? 1 : 0)

/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
/*   Г Л О Б А Л Ь Н Ы Е   П Е Р Е М Е Н Н Ы Е  И  К О Н С Т А Н Т Ы   */
/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/

//первичное объявление глобальной переменной
//глобальная переменная действует от места объявления до конца файла
//, в котором объявлена

HDC hdc;	// Объявим контекст устройства
			// Контекст устройства по сути это структура,
			// содержащая описание видеокарты на вашем компьютере
			// и всех необходимых графических элементов

/***********************************************************************/
/*               О С Н О В Н А Я    П Р О Г Р А М М А                  */
/***********************************************************************/
int kills = 0;
int InitX = 200; //начальные координаты платформы
int InitY = 200; //начальные координаты платформы
int Step = 5; //шаг с которым передвигаем платформу

Car car(InitX, InitY);
Obstacle *collisiable[6];


void checkRespawn();
void checkCollision();
void spawnObstacles();
void showObstacles();
bool isCollision(CollisionRect& one, CollisionRect& two);

int main()
{
	HWND hwnd = GetConcolWindow();
	system("color F0");

	if (hwnd != NULL)
	{
		//получим контекст устройства для консольного окна
		hdc = GetWindowDC(hwnd);
		//если контекст существует - можем работать
		if (hdc != 0)
		{	
			spawnObstacles();
			//car.Show();
			while (1)	//бесконечный цикл буксировки фигуры
			{
				checkCollision();
				checkRespawn();
				showObstacles();
				if (KEY_DOWN(VK_ESCAPE))
					break;

				if (KEY_DOWN(VK_LEFT))
				{
					InitX -= Step;
					car.MoveTo(InitX, InitY);
					Sleep(5);
				}

				if (KEY_DOWN(VK_RIGHT)) 
				{
					InitX += Step;
					car.MoveTo(InitX, InitY);
					Sleep(5);
				}

				if (KEY_DOWN(VK_UP)) 
				{
					InitY -= Step;
					car.MoveTo(InitX, InitY);
					Sleep(5);
				}

				if (KEY_DOWN(VK_DOWN)) 
				{
					InitY += Step;
					car.MoveTo(InitX, InitY);
					Sleep(5);
				}

				if (KEY_DOWN(VK_SPACE)) {
					
				}
			}
		}
	}
	return 0;
}

void showObstacles() {
	for (int i = 0; i < 6; i++)
	{
		if (collisiable[i])
		{
			collisiable[i]->Show();
		}
	}
}

void checkRespawn() {
	if (kills % 4 == 0)
	{
		spawnObstacles();
	}
}

void checkCollision() {
	for (int i = 0; i < 6; i++)
	{
		if (collisiable[i] == 0) continue;
		
		if (!isCollision(car, *collisiable[i])) continue;
		
		switch (collisiable[i]->GetType())
		{
		case DEACREASE:
			switch (rand()%3)
			{
			case 0:
				car.DeacreaseBody();
				break;
			case 1:
				car.DeacreaseRoof();
				break;
			case 2:
				car.DeacreaseWheel();
				break;
			}
			car.DeacreaseSize();
			break;
		case INCREASE:
			switch (rand() % 3)
			{
			case 0:
				car.IncreaseBody();
				break;
			case 1:
				car.IncreaseRoof();
				break;
			case 2:
				car.IncreaseWheel();
				break;
			}
			car.IncreaseSize();
			break;
		}

		collisiable[i]->Hide();
		collisiable[i] = 0;
		kills++;
	}
}

void spawnObstacles() {
	for (int i = 0; i < 6; ++i) {
		if (i % 2)
		{
			collisiable[i] = new DecreaseObstacle(50 + i * 75, 250 + i * 15, 10);
		}
		else {
			collisiable[i] = new IncreaseObstacle(75 + i * 75, 750 + i * 15, 10);
		}
	}
}

bool isCollision(CollisionRect& one, CollisionRect& two) {
	double oneX = (double)one.GetX() - (one.GetCollisionWidth());
	double twoX = (double)two.GetX() - (two.GetCollisionWidth());

	double oneY = (double)one.GetY() - (one.GetCollisionHeight());
	double twoY = (double)two.GetY() - (two.GetCollisionHeight());

	bool xcoll = oneX < twoX + two.GetCollisionWidth() * 2 && oneX + one.GetCollisionWidth() * 2 > twoX;
	bool ycoll = oneY < twoY + two.GetCollisionHeight() * 2 && oneY + one.GetCollisionHeight() * 2 > twoY;

	return xcoll and ycoll;

	//bool xcoll = first.GetX() < second.GetX() + second.GetCollisionWidth() * 2 && first.GetX() + first.GetCollisionWidth() * 2 > second.GetX();
	//bool ycoll = first.GetY() < second.GetY() + second.GetCollisionHeight() * 2 && first.GetY() + first.GetCollisionHeight() * 2 > second.GetY();
}