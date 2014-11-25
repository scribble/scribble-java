/**
 */
package org.scribble.trace.editor.dsl.scribbleTraceDsl.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.scribble.trace.editor.dsl.scribbleTraceDsl.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ScribbleTraceDslFactoryImpl extends EFactoryImpl implements ScribbleTraceDslFactory
{
  /**
   * Creates the default factory implementation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static ScribbleTraceDslFactory init()
  {
    try
    {
      ScribbleTraceDslFactory theScribbleTraceDslFactory = (ScribbleTraceDslFactory)EPackage.Registry.INSTANCE.getEFactory(ScribbleTraceDslPackage.eNS_URI);
      if (theScribbleTraceDslFactory != null)
      {
        return theScribbleTraceDslFactory;
      }
    }
    catch (Exception exception)
    {
      EcorePlugin.INSTANCE.log(exception);
    }
    return new ScribbleTraceDslFactoryImpl();
  }

  /**
   * Creates an instance of the factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ScribbleTraceDslFactoryImpl()
  {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public EObject create(EClass eClass)
  {
    switch (eClass.getClassifierID())
    {
      case ScribbleTraceDslPackage.TRACE: return createTrace();
      case ScribbleTraceDslPackage.STEPDEFN: return createStepdefn();
      case ScribbleTraceDslPackage.MESSAGETRANSFER: return createMessagetransfer();
      case ScribbleTraceDslPackage.PARAMETER: return createParameter();
      default:
        throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
    }
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Trace createTrace()
  {
    TraceImpl trace = new TraceImpl();
    return trace;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Stepdefn createStepdefn()
  {
    StepdefnImpl stepdefn = new StepdefnImpl();
    return stepdefn;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Messagetransfer createMessagetransfer()
  {
    MessagetransferImpl messagetransfer = new MessagetransferImpl();
    return messagetransfer;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Parameter createParameter()
  {
    ParameterImpl parameter = new ParameterImpl();
    return parameter;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public ScribbleTraceDslPackage getScribbleTraceDslPackage()
  {
    return (ScribbleTraceDslPackage)getEPackage();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @deprecated
   * @generated
   */
  @Deprecated
  public static ScribbleTraceDslPackage getPackage()
  {
    return ScribbleTraceDslPackage.eINSTANCE;
  }

} //ScribbleTraceDslFactoryImpl
