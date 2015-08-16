package game.sticks

/**
 * Created by aleapv on 16.08.2015.
 */
object Sticks {

  import org.scalacheck._
  import game.sticks.Sticks.Color._
  val throwSticksGen = Gen.containerOfN[List, Color](4, Gen.oneOf(White, Black))

  val sticks = List.fill(4)(Stick)

  import game.sticks.Sticks.Color._
  object Stick {
    val blackSide = Black
    val whiteSide = White
  }

  // TODO what is the pattern?
  sealed trait Color
  object Color {
    object Black extends Color
    object White extends Color

    def getColor(c: Color): Short = c match {
      case Black => 1
      case White => 2
    }
  }

  sealed trait ST[S,A] { self =>
    protected def run(s: S): (A,S)
    def map[B](f:A => B): ST[S,B] = new ST[S,B] {
      def run(s: S) = {
        val(a, s1) = self.run(s)
        (f(a), s1)
      }
    }
    def flatMap[B](f: A => ST[S,B]): ST[S,B] = new ST[S,B] {
      def run (s: S) = {
        val (a, s1) = self.run(s)
        f(a).run(s1)
      }
    }
  }
  object ST {
    def apply[S,A](a: => A) = {
      lazy val memo = a
      new ST[S,A] {
        def run(s: S) = (memo, s)
      }
    }
    def runST[A](st: RunnableST[A]): A =
      st.apply[Unit].run(())._1
  }
  sealed trait STRef[S,A] {
    protected var cell: A
    def read: ST[S,A] = ST(cell)
    def write(a: A): ST[S, Unit] = new ST[S, Unit] {
      def run(s: S) = {
        cell = a
        ((), s)
      }
    }
  }
  object STRef {
    def apply[S,A](a: A): ST[S, STRef[S,A]] = ST(new STRef[S,A] {
      var cell = a
    })
  }
  trait RunnableST[A] {
    def apply[S]: ST[S,A]
  }
  val p = new RunnableST[(Int,Int)] {
    def apply[S] = for {
      r1 <- STRef(1)
      r2 <- STRef(2)
      x <- r1.read
      y <- r2.read
      _ <- r1.write(y+1)
      _ <- r2.write(x+1)
      a <- r1.read
      b <- r2.read
    } yield (a,b)
  }
  val r = ST.runST(p)
}