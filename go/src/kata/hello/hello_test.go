package kata

import ( "testing" 
 )

func TestHello(t *testing.T) {
		want := "sayHello: hello world"
		got := SayHello()
		if got != want {
			t.Errorf("SayHello() == %q, want %q", got, want)
		}
	}
