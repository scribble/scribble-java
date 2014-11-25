/**
 */
package org.scribble.trace.editor.dsl.scribbleTraceDsl.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.scribble.trace.editor.dsl.scribbleTraceDsl.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.ScribbleTraceDslPackage
 * @generated
 */
public class ScribbleTraceDslAdapterFactory extends AdapterFactoryImpl
{
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static ScribbleTraceDslPackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ScribbleTraceDslAdapterFactory()
  {
    if (modelPackage == null)
    {
      modelPackage = ScribbleTraceDslPackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object)
  {
    if (object == modelPackage)
    {
      return true;
    }
    if (object instanceof EObject)
    {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected ScribbleTraceDslSwitch<Adapter> modelSwitch =
    new ScribbleTraceDslSwitch<Adapter>()
    {
      @Override
      public Adapter caseTrace(Trace object)
      {
        return createTraceAdapter();
      }
      @Override
      public Adapter caseStepdefn(Stepdefn object)
      {
        return createStepdefnAdapter();
      }
      @Override
      public Adapter caseMessagetransfer(Messagetransfer object)
      {
        return createMessagetransferAdapter();
      }
      @Override
      public Adapter caseParameter(Parameter object)
      {
        return createParameterAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object)
      {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target)
  {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.Trace <em>Trace</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.Trace
   * @generated
   */
  public Adapter createTraceAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.Stepdefn <em>Stepdefn</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.Stepdefn
   * @generated
   */
  public Adapter createStepdefnAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.Messagetransfer <em>Messagetransfer</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.Messagetransfer
   * @generated
   */
  public Adapter createMessagetransferAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link org.scribble.trace.editor.dsl.scribbleTraceDsl.Parameter <em>Parameter</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see org.scribble.trace.editor.dsl.scribbleTraceDsl.Parameter
   * @generated
   */
  public Adapter createParameterAdapter()
  {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter()
  {
    return null;
  }

} //ScribbleTraceDslAdapterFactory
