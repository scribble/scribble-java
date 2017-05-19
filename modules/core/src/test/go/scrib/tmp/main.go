//$Raymond@HZHL3 ~/code/scribble-neon/github-rhu1-go/scribble-java/modules/core/src/test/go/scrib/tmp
//$ go build
//$ ./tmp.exe


package main;


import (
	"log"
	"sync"

	"scrib/tmp/Test/Proto1"	
)


var (
	barrier = new(sync.WaitGroup)
)


func main() {
	log.Println("chan transport")
	barrier.Add(2)

	AB := make(chan Proto1.T)	

	go RunA(AB)
	go RunB(AB)

	barrier.Wait()
}

func RunA(AB chan Proto1.T) {
	log.Println("A: start")
	defer barrier.Done()

	A, endA := Proto1.NewA(AB)
	defer endA.Close()
	var y int

	A.Sen

	A.Send_B_1(1234).Recv_B_2(&y)

	log.Println("A: received form B:", y)
}


func RunB(BA chan Proto1.T) {
	log.Println("B: start")
	defer barrier.Done()

	B, endB := Proto1.NewB(BA)
	defer endB.Close()
	var x int

	B.Recv_A_1(&x).Send_A_2(x * 2)

	log.Println("B: received from A:", x)
}