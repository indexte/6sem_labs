package main

import (
	"fmt"
	"math/rand"
	"strconv"
	"sync"
	"time"
)

var blocklist = [7]bool{false, false, false, false, false, false, false}
var blocked_time = [7]int{0, 0, 0, 0, 0, 0, 0}
var rates = [5]int{0, 0, 0, 0, 0}
var paid = false
var winner = -1
var paying = false
var closed = false

func Auction(Semaphore chan bool, waitGroup *sync.WaitGroup)  {
	defer waitGroup.Done()
	for i := 0; i < 5; i++ {
		time.Sleep(10*time.Second)
		Semaphore <- true
		fmt.Println("Trades are end")

		for j := 0; j < 7; j++ {
			if blocklist[j] == false {
				if rates[j] > winner { winner = j }
			}
			rates[j] = 0
		}
		fmt.Println("Winner " + strconv.Itoa(winner) + " has 5 sec to pay.")

		paying = true
		<- Semaphore

		time.Sleep(5*time.Second)
		Semaphore <- true

		if paid {
			for j := 0; j < 7; j++ {
				if blocked_time[j] > 0 {
					blocked_time[j]--
					if blocked_time[j] == 0 {
						fmt.Println("player " + strconv.Itoa(j) + " unblocked.")
						blocklist[j] = false
					}
				}
			}
		} else {
			fmt.Println("Winner " + strconv.Itoa(winner) + " blocked.")
			blocklist[winner] = true
			blocked_time[winner] = 2
		}

		paid = false
		winner = -1
		paying = false
		<- Semaphore
	}
	closed = true
}

func Participant(Semaphore chan bool, waitGroup *sync.WaitGroup, num int, pay bool){
	defer waitGroup.Done()
	for {
		time.Sleep(time.Second)
		Semaphore <- true

		if paying {
			if winner == num {
				if pay {
					paid = true
					fmt.Println(strconv.Itoa(num) + " pays lot")
				} else {
					fmt.Println(strconv.Itoa(num) + " won't pay lot")
				}
			}
		} else {
			change := rand.Intn(1 - 0 + 1)
			if change == 1 {
				increase := rand.Intn(1 - 0 + 1)
				if increase == 0{
					if rates[num] > 0 { rates[num]-- }
					fmt.Println(strconv.Itoa(num) + " sets rate " + strconv.Itoa(rates[num]))
				} else {
					rates[num]++
					fmt.Println(strconv.Itoa(num) + " sets rate " + strconv.Itoa(rates[num]))
				}
			}
		}
		<- Semaphore
		if closed { break }
	}
}

func main() {
	var waitGroup sync.WaitGroup

	var Semaphore = make(chan bool, 1)

	waitGroup.Add(1)
	go Auction(Semaphore, &waitGroup)

	waitGroup.Add(1)
	go Participant(Semaphore, &waitGroup, 1, true)
	waitGroup.Add(1)
	go Participant(Semaphore, &waitGroup, 2, true)
	waitGroup.Add(1)
	go Participant(Semaphore, &waitGroup, 3, true)
	waitGroup.Add(1)
	go Participant(Semaphore, &waitGroup, 4, false)

	waitGroup.Wait()
}
