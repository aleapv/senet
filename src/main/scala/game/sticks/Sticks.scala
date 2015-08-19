package game.sticks

/**
 * Created by aleapv on 16.08.2015.
 */
object Sticks {

  import org.scalacheck._
  import game.sticks.Sticks.Color._
  val throwStickGen = Gen.oneOf(White, Black)

  val sticks = List.fill(4)(Stick(Black))

  case class Stick(c: Color)

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
  // TODO change ST for scalaz implementation
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

  def throwSticksST = new RunnableST[List[Stick]] {
    def apply[S] = for {
      st1 <- STRef(Stick(throwStickGen.sample.get))
      st2 <- STRef(Stick(throwStickGen.sample.get))
      st3 <- STRef(Stick(throwStickGen.sample.get))
      st4 <- STRef(Stick(throwStickGen.sample.get))
      s1 <- st1.read
      s2 <- st2.read
      s3 <- st3.read
      s4 <- st4.read
    } yield s1::s2::s3::s4::Nil
  }
  def throwSticks = ST.runST(throwSticksST)
}