//
// Generated by JTB 1.3.2
//

package syntaxtree;

/**
 * Grammar production:
 * f0 -> <SCOMMENT1>
 * f1 -> Identifier()
 * f2 -> "alias?"
 * f3 -> Identifier()
 * f4 -> <SCOMMENT2>
 */
public class Query implements Node {
   public NodeToken f0;
   public Identifier f1;
   public NodeToken f2;
   public Identifier f3;
   public NodeToken f4;

   public Query(NodeToken n0, Identifier n1, NodeToken n2, Identifier n3, NodeToken n4) {
      f0 = n0;
      f1 = n1;
      f2 = n2;
      f3 = n3;
      f4 = n4;
   }

   public Query(Identifier n0, Identifier n1) {
      f0 = new NodeToken("/*");
      f1 = n0;
      f2 = new NodeToken("alias?");
      f3 = n1;
      f4 = new NodeToken("*/");
   }

   public void accept(visitor.Visitor v) {
      v.visit(this);
   }
   public <R,A> R accept(visitor.GJVisitor<R,A> v, A argu) {
      return v.visit(this,argu);
   }
   public <R> R accept(visitor.GJNoArguVisitor<R> v) {
      return v.visit(this);
   }
   public <A> void accept(visitor.GJVoidVisitor<A> v, A argu) {
      v.visit(this,argu);
   }
}
