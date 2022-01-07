#pragma once                  
#include <windows.h>
#include <conio.h>
#include <iostream>   
#include <thread>
#include <chrono>
using namespace std;        // Пространство имен std
#include "GetConsolWindow.h"	
#include "Ball.h"	
#include "Platform.h"	
#include "EnemyPlatform.h"	
#include "StrongPlatform.h"	
#include "TransparentPlatform.h"	
#include "Wall.h"
#include "PLATFORMTYPE.h"
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


int CounterKills = 0;

int InitBallX = 100;
int InitBallY = 500;

int InitX = 200; //начальные координаты платформы
int InitY = 900; //начальные координаты платформы
int Step = 25; //шаг с которым передвигаем платформу

int Score = 1; //количество очков
char result[16]; //строка с результатом

Ball ball(InitBallX, InitBallY, 10);

const int sizePlatfX = 11;
const int sizePlatfY = 10;
EnemyPlatform* platforms[sizePlatfX][sizePlatfY];

Wall leftWall(15, 0, 10, 1000);
Wall rightWall(900, 0, 10, 1000);
Wall topWall(450, 50, 450, 10);

Platform platform(InitX, InitY, 100, 10);

CollisionRect collisiable[3];

bool isStarted = false;

bool isCollision(CollisionRect& first, CollisionRect& second);
void UpdateScore();
void IncreaseScore();
void SpawnPlatforms();
void CheckPlatformCollision();
void ShowObjects();
void CheckWallCollision();
void CheckLoose();
void CheckRespawn();

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
			collisiable[0] = leftWall;
			collisiable[1] = rightWall;
			collisiable[2] = topWall;
			
			SpawnPlatforms();
			
			while (1)	//бесконечный цикл буксировки фигуры
			{
				if (isStarted)
				{
					ball.MoveTo();
					CheckPlatformCollision();
					CheckWallCollision();
					ShowObjects();
					CheckLoose();
				}

				if (KEY_DOWN(VK_ESCAPE))  
					break;

				if (KEY_DOWN(VK_LEFT)) //стрелка влево  37
				{
					InitX -= Step;
					platform.MoveTo(InitX, InitY);
					Sleep(5);
				}

				if (KEY_DOWN(VK_RIGHT)) //стрелка вправо  39
				{
					InitX += Step;
					platform.MoveTo(InitX, InitY);
					Sleep(5);
				}
				if (KEY_DOWN(VK_SPACE)) {
					isStarted = true;
					ball.MoveTo(platform.GetX(), platform.GetY()-100);
					ball.SetDirection(RIGHT_UP);
				}
			}
		}
	}
	return 0;
}

void CheckRespawn() {
	if (CounterKills > 3*(sizePlatfX * sizePlatfY)/2)
	{
		CounterKills = 0;
		SpawnPlatforms();
	}
}

void CheckLoose() {
	if (ball.GetY() > 1000)
	{
		isStarted = false;
		Score = 1;
		UpdateScore();
		SpawnPlatforms();
		ball.MoveTo(platform.GetX(), platform.GetY()-100);
		ball.SetDirection(RIGHT_UP);
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

void ShowObjects() {
	for (int i = 0; i < sizePlatfX; i++)
	{
		for (int j = 0; j < sizePlatfY; j++)
		{
			if (platforms[i][j] != 0)
				platforms[i][j]->Show();
		}
	}
	leftWall.Show();
	rightWall.Show();
	topWall.Show();
}

void CheckPlatformCollision() {

	for (int i = 0; i < sizePlatfX; i++)
	{
		for (int j = 0; j < sizePlatfY; j++)
		{
			if (platforms[i][j] != 0)
			{
				if (isCollision(*platforms[i][j], ball))
				{
					switch (platforms[i][j]->GetType()) {
					case ENEMY_PLATFORM:
						platforms[i][j]->Damage();
						ball.ChangeDirection();
						break;
					case TRANSPARENT_PLATFORM:
						platforms[i][j]->Damage();
						break;
					case STRONG_PLATFORM:
						platforms[i][j]->Damage();
						ball.ChangeDirection();
						break;
					}
					if (platforms[i][j]->GetHealth() == 0)
					{
						platforms[i][j]->Hide();
						platforms[i][j] = 0;
						CounterKills++;
					}
					IncreaseScore();
				}

			}
			
		}
	}
}

void CheckWallCollision() {
	for (int i = 0; i < 3; i++)
	{
		if (isCollision(collisiable[i], ball) || isCollision(platform, ball))
		{
			ball.ChangeDirection();
		}
	}
}

void SpawnPlatforms() {
	for (int i = 0; i < sizePlatfX; i++)
	{
		for (int j = 0; j < sizePlatfY; j++)
		{
			int coordX;
			int coordY = 100 + (25 + j * 3) * j;
			int width = 30;
			int height = 10;

			if (j % 2 == 0) {
				coordX = 100 + 70 * i + 4;
			}
			else {
				coordX = 100 + 70 * i - 4;
			}

			switch ((j + i) % 3)
			{
			case ENEMY_PLATFORM:
				platforms[i][j] = new EnemyPlatform(coordX, coordY, width, height);
				break;
			case TRANSPARENT_PLATFORM:
				platforms[i][j] = new TransparentPlatform(coordX, coordY, width, height);
				break;
			case STRONG_PLATFORM:
				platforms[i][j] = new StrongPlatform(coordX, coordY, width, height);
				break;
			}

		}
	}
}

void UpdateScore() {
	_itoa_s(Score, result, 10);
	TextOutA(hdc, 450, 1000, (LPCSTR)result, log10(Score) + 1);
}

void IncreaseScore() {
	CheckRespawn();
	if (Score % 10 == 0)
	{
		ball.IncreaseColorLevel();
	}
	if (Score % 15 == 0)
	{
		ball.IncreaseShapeLevel();
	}
	Score++;
	UpdateScore();
}