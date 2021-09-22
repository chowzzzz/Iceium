/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.session.stateless.OrderEntitySessionBeanLocal;
import ejb.session.stateless.OrderItemEntitySessionBeanLocal;
import entity.OrderEntity;
import entity.OrderItemEntity;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.event.SelectEvent;
import util.enumeration.OrderItemStatusEnum;
import util.enumeration.PaymentMethodEnum;
import util.exception.OrderItemNotFoundException;

/**
 *
 * @author hilary
 */
@Named(value = "orderManagementManagedBean")
@ViewScoped
public class OrderManagementManagedBean implements Serializable
{

    @EJB(name = "OrderItemEntitySessionBeanLocal")
    private OrderItemEntitySessionBeanLocal orderItemEntitySessionBeanLocal;

    @EJB(name = "OrderEntitySessionBeanLocal")
    private OrderEntitySessionBeanLocal orderEntitySessionBeanLocal;

    @Inject
    private ViewOrderManagedBean viewOrderManagedBean;

    private List<OrderEntity> orderEntities;
    private List<OrderEntity> filteredOrderEntities;

    private OrderEntity selectedOrderEntityToUpdate;
    private List<OrderItemEntity> filteredOrderItemEntities;

    private OrderItemStatusEnum filterOrderItemEnum;

    public OrderManagementManagedBean()
    {
        orderEntities = new ArrayList<>();
    }

    @PostConstruct
    public void postConstruct()
    {
        orderEntities = orderEntitySessionBeanLocal.retrieveAllOrders();
    }

    public void filterOrderItemStatusChange(SelectEvent event)
    {
        String selectedOrderItemStatusEnum = (String) event.getObject();

        if (selectedOrderItemStatusEnum == null)
        {
            filteredOrderItemEntities = new ArrayList<>(selectedOrderEntityToUpdate.getOrderItemEntities());
        }
        else
        {
            filterOrderItemEnum = OrderItemStatusEnum.valueOf(selectedOrderItemStatusEnum);
            filteredOrderItemEntities.clear();
            for (int i = 0; i < selectedOrderEntityToUpdate.getOrderItemEntities().size(); i++)
            {
                if (selectedOrderEntityToUpdate.getOrderItemEntities().get(i).getOrderItemStatusEnum().equals(filterOrderItemEnum))
                {
                    filteredOrderItemEntities.add(selectedOrderEntityToUpdate.getOrderItemEntities().get(i));
                }
            }
        }
    }

    public void updateOrderItemStatusChange(SelectEvent event)
    {
        OrderItemStatusEnum selectedOrderItemStatusEnum = (OrderItemStatusEnum) event.getObject();
        OrderItemEntity orderItemEntityToUpdate = (OrderItemEntity) event.getComponent().getAttributes().get("orderItemEntityToUpdate");

        for (OrderItemEntity orderItemEntity : selectedOrderEntityToUpdate.getOrderItemEntities())
        {
            if (orderItemEntity.getOrderItemId().equals(orderItemEntityToUpdate.getOrderItemId()))
            {
                orderItemEntity.setOrderItemStatusEnum(selectedOrderItemStatusEnum);
            }
        }

        if (filteredOrderItemEntities != null)
        {
            for (int i = filteredOrderItemEntities.size() - 1; i >= 0; i--)
            {
                if (filteredOrderItemEntities.get(i).getOrderItemId().equals(orderItemEntityToUpdate.getOrderItemId()))
                {
                    if (filterOrderItemEnum == null)
                    {
                        filteredOrderItemEntities.get(i).setOrderItemStatusEnum(selectedOrderItemStatusEnum);
                    }
                    else if (!selectedOrderItemStatusEnum.equals(filterOrderItemEnum))
                    {
                        filteredOrderItemEntities.remove(i);
                    }
                }
            }
        }
    }

    public void doUpdateOrder(ActionEvent event)
    {
        selectedOrderEntityToUpdate = (OrderEntity) event.getComponent().getAttributes().get("orderEntityToUpdate");
        filteredOrderItemEntities = new ArrayList<>(selectedOrderEntityToUpdate.getOrderItemEntities());
    }

    public void updateOrder()
    {
        try
        {
            for (OrderItemEntity orderItemEntity : selectedOrderEntityToUpdate.getOrderItemEntities())
            {
                orderItemEntitySessionBeanLocal.updateOrderItem(orderItemEntity.getOrderItemId(), orderItemEntity.getOrderItemStatusEnum());
            }
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Order updated successfully", null));
        }
        catch (OrderItemNotFoundException ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred while updating order: " + ex.getMessage(), null));
        }
        catch (Exception ex)
        {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An unexpected error has occurred: " + ex.getMessage(), null));
        }
    }

    public List<OrderEntity> getOrderEntities()
    {
        return orderEntities;
    }

    public void setOrderEntities(List<OrderEntity> orderEntities)
    {
        this.orderEntities = orderEntities;
    }

    public ViewOrderManagedBean getViewOrderManagedBean()
    {
        return viewOrderManagedBean;
    }

    public void setViewOrderManagedBean(ViewOrderManagedBean viewOrderManagedBean)
    {
        this.viewOrderManagedBean = viewOrderManagedBean;
    }

    public List<OrderEntity> getFilteredOrderEntities()
    {
        return filteredOrderEntities;
    }

    public void setFilteredOrderEntities(List<OrderEntity> filteredOrderEntities)
    {
        this.filteredOrderEntities = filteredOrderEntities;
    }

    public OrderEntity getSelectedOrderEntityToUpdate()
    {
        return selectedOrderEntityToUpdate;
    }

    public void setSelectedOrderEntityToUpdate(OrderEntity selectedOrderEntityToUpdate)
    {
        this.selectedOrderEntityToUpdate = selectedOrderEntityToUpdate;
    }

    public List<OrderItemEntity> getFilteredOrderItemEntities()
    {
        return filteredOrderItemEntities;
    }

    public void setFilteredOrderItemEntities(List<OrderItemEntity> filteredOrderItemEntities)
    {
        this.filteredOrderItemEntities = filteredOrderItemEntities;
    }

    public PaymentMethodEnum[] getPaymentMethodEnums()
    {
        return PaymentMethodEnum.values();
    }

    public OrderItemStatusEnum[] getOrderItemStatusEnums()
    {
        return OrderItemStatusEnum.values();
    }
}
